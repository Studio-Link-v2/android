#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_link_studio_app_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "1299abcdef";
    return env->NewStringUTF(hello.c_str());
}
