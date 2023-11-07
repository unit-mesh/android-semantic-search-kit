## Model

Intellij IDEA:

- semantic-text-search-0.0.1.jar, 88.4M - [multi-qa-MiniLM-L6-cos-v1](https://packages.jetbrains.team/maven/p/ml-search-everywhere/local-models/org/jetbrains/intellij/searcheverywhereMl/semantics/semantic-text-search/0.0.1/semantic-text-search-0.0.1.jar)
- semantic-text-search-0.0.2.jar, 88.4M - [multi-qa-MiniLM-L6-cos-v1](https://packages.jetbrains.team/maven/p/ml-search-everywhere/local-models/org/jetbrains/intellij/searcheverywhereMl/semantics/semantic-text-search/0.0.2/semantic-text-search-0.0.2.jar)
- semantic-text-search-0.0.3.jar, 9.6M - [dan-bert-tiny](https://packages.jetbrains.team/maven/p/ml-search-everywhere/local-models/org/jetbrains/intellij/searcheverywhereMl/semantics/semantic-text-search/0.0.3/semantic-text-search-0.0.3.jar)

Bloop: 

[all-MiniLM-L6-v2](https://github.com/BloopAI/bloop/tree/95559bf47dbe40497f01665184d194726378e800/apps/desktop/src-tauri/model), 21.9M

## Know issue

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

