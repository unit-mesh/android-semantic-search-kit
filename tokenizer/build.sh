#!/usr/bin/env bash

set -e
WORK_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
NUM_PROC=1
if [[ -n $(command -v nproc) ]]; then
  NUM_PROC=$(nproc)
elif [[ -n $(command -v sysctl) ]]; then
  NUM_PROC=$(sysctl -n hw.ncpu)
fi
PLATFORM=$(uname | tr '[:upper:]' '[:lower:]')

VERSION=v$1
ARCH=$2
ANDROID_ARCH=$3

pushd $WORK_DIR
if [ ! -d "../vendors/tokenizers" ]; then
  git clone https://github.com/huggingface/tokenizers ../vendors/tokenizers -b $VERSION
fi

if [ ! -d "build" ]; then
  mkdir build
fi

rm -rf build/classes
mkdir build/classes
javac -sourcepath src/main/java/ src/main/java/org/unitmesh/tokenizer/huggingface/tokenizers/jni/TokenizersLibrary.java -h build/include -d build/classes

RUST_MANIFEST=rust/Cargo.toml
cargo build --manifest-path $RUST_MANIFEST --release

mkdir -p libs/$ANDROID_ARCH
cp -f rust/target/$ARCH/release/libdjl.so libs/$ANDROID_ARCH/libtokenizers.so
