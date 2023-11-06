##

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

