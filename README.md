# Android LLM PoC

## Quick Start and failed

参考：[GitHub Workflows](.github/workflows/ci.yml)

### 本地构建（暂时不保证成功）

```bash
git submodule update --init
```

1.setup NDK

需要把 NDK 换成你的本地 NDK 路径和版本

```bash
export ANDROID_HOME=$HOME/Library/Android/sdk
export ANDROID_NDK_HOME=$HOME/Library/Android/sdk/ndk/26.1.10909125

export TOOLCHAIN=$ANDROID_NDK_HOME/toolchains/llvm/prebuilt/darwin-x86_64
export TARGET=aarch64-linux-android
export API=34

export AR=$TOOLCHAIN/bin/llvm-ar
export CC=$TOOLCHAIN/bin/$TARGET$API-clang
export AS=$CC
export CXX=$TOOLCHAIN/bin/$TARGET$API-clang++
export LD=$TOOLCHAIN/bin/ld
export RANLIB=$TOOLCHAIN/bin/llvm-ranlib
export STRIP=$TOOLCHAIN/bin/llvm-strip

export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin
export PATH=$PATH:$TOOLCHAIN/bin
```

2.setup Rust target

```bash
rustup target add armv7-linux-androideabi i686-linux-android arm-linux-androideabi x86_64-linux-android aarch64-linux-android
```

3.config linker

After that add the following like to `$HOME/.cargo/config` (make the config file if it doesn't exist):

Follow: [https://doc.rust-lang.org/beta/rustc/platform-support.html](https://doc.rust-lang.org/beta/rustc/platform-support.html)

```toml
[target.i686-linux-android]
linker = "/Users/phodal/Library/Android/sdk/ndk/26.1.10909125/toolchains/llvm/prebuilt/darwin-x86_64/bin/i686-linux-android34-clang"

[target.arm-linux-android]
linker = "/Users/phodal/Library/Android/sdk/ndk/26.1.10909125/toolchains/llvm/prebuilt/darwin-x86_64/bin/arm-linux-android34-clang"

[target.x86_64-linux-android]
linker = "/Users/phodal/Library/Android/sdk/ndk/26.1.10909125/toolchains/llvm/prebuilt/darwin-x86_64/bin/x86_64-linux-android34-clang"

[target.aarch64-linux-android]
linker = "/Users/phodal/Library/Android/sdk/ndk/26.1.10909125/toolchains/llvm/prebuilt/darwin-x86_64/bin/aarch64-linux-android34-clang"
```

4.build ffi

```
./gradlew cargoBuild
```

for iOS (sample, not working)

```
rustup target add aarch64-apple-ios
cargo build --target=x86_64-apple-ios --release
```

5.run app

## Model

IntelliJ IDEA Search Everywhere Model:

- semantic-text-search-0.0.1.jar, 88.4M - [multi-qa-MiniLM-L6-cos-v1](https://packages.jetbrains.team/maven/p/ml-search-everywhere/local-models/org/jetbrains/intellij/searcheverywhereMl/semantics/semantic-text-search/0.0.1/semantic-text-search-0.0.1.jar)
- semantic-text-search-0.0.2.jar, 88.4M - [multi-qa-MiniLM-L6-cos-v1](https://packages.jetbrains.team/maven/p/ml-search-everywhere/local-models/org/jetbrains/intellij/searcheverywhereMl/semantics/semantic-text-search/0.0.2/semantic-text-search-0.0.2.jar)
- semantic-text-search-0.0.3.jar, 9.6M - [dan-bert-tiny](https://packages.jetbrains.team/maven/p/ml-search-everywhere/local-models/org/jetbrains/intellij/searcheverywhereMl/semantics/semantic-text-search/0.0.3/semantic-text-search-0.0.3.jar)

Bloop Model: 

- [all-MiniLM-L6-v2](https://github.com/BloopAI/bloop/tree/95559bf47dbe40497f01665184d194726378e800/apps/desktop/src-tauri/model), 21.9M

## FAQ

```
Caused by: ai.djl.engine.EngineException: Failed to load Huggingface native library.
    at ai.djl.huggingface.tokenizers.jni.LibUtils.<clinit>(LibUtils.java:43)
    at ai.djl.huggingface.tokenizers.jni.LibUtils.checkStatus(LibUtils.java:50)
    at ai.djl.huggingface.tokenizers.HuggingFaceTokenizer.newInstance(HuggingFaceTokenizer.java:170)
    at org.unitmesh.llmpoc.embedding.STSemantic$Companion.create(STSemantic.kt:78)
    at org.unitmesh.llmpoc.MainActivity.onCreate(MainActivity.kt:33)
    at android.app.Activity.performCreate(Activity.java:8143)
    at android.app.Activity.performCreate(Activity.java:8114)
    at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1310)
    at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3513)
    at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3700) 
    at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:85) 
    at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135) 
    at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95) 
    at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2136) 
    at android.os.Handler.dispatchMessage(Handler.java:106) 
    at android.os.Looper.loop(Looper.java:236) 
    at android.app.ActivityThread.main(ActivityThread.java:8060) 
    at java.lang.reflect.Method.invoke(Native Method) 
    at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:656) 
    at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:967) 
Caused by: java.lang.IllegalStateException: Cannot copy jni files
    at ai.djl.huggingface.tokenizers.jni.LibUtils.copyJniLibraryFromClasspath(LibUtils.java:108)
    at ai.djl.huggingface.tokenizers.jni.LibUtils.loadLibrary(LibUtils.java:66)
    at ai.djl.huggingface.tokenizers.jni.LibUtils.<clinit>(LibUtils.java:41)
    at ai.djl.huggingface.tokenizers.jni.LibUtils.checkStatus(LibUtils.java:50) 
    at ai.djl.huggingface.tokenizers.HuggingFaceTokenizer.newInstance(HuggingFaceTokenizer.java:170) 
    at org.unitmesh.llmpoc.embedding.STSemantic$Companion.create(STSemantic.kt:78) 
    at org.unitmesh.llmpoc.MainActivity.onCreate(MainActivity.kt:33) 
    at android.app.Activity.performCreate(Activity.java:8143) 
    at android.app.Activity.performCreate(Activity.java:8114) 
    at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1310) 
    at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3513) 
    at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3700) 
    at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:85) 
    at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135) 
    at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95) 
    at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2136) 
    at android.os.Handler.dispatchMessage(Handler.java:106) 
    at android.os.Looper.loop(Looper.java:236) 
    at android.app.ActivityThread.main(ActivityThread.java:8060) 
    at java.lang.reflect.Method.invoke(Native Method) 
    at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:656) 
    at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:967) 
```

Issue: [2170](https://github.com/deepjavalibrary/djl/issues/2170)

Solution: [Rust Android Gradle Plugin](https://github.com/mozilla/rust-android-gradle)

### arm-linux-androideabi-ranlib not found

Error building Rust project for Android (Flutter): arm-linux-androideabi-ranlib not found for OpenSSL compilation

https://stackoverflow.com/questions/75943717/error-building-rust-project-for-android-flutter-arm-linux-androideabi-ranlib




