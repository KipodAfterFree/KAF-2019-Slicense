#include <jni.h>
#include <string>

extern "C" JNIEXPORT jboolean JNICALL
Java_com_kipodafterfree_f00bar_app_KeyValidator_fewnfewnki(
        JNIEnv* env,
        jobject,
        jstring license,
        jstring key) {

    std::string s = "scott>=tiger>=mushroom";
    std::string delimiter = ">=";

    size_t pos = 0;
    std::string token;
    while ((pos = s.find(delimiter)) != std::string::npos) {
        token = s.substr(0, pos);
        std::cout << token << std::endl;
        s.erase(0, pos + delimiter.length());
    }
    std::cout << s << std::endl;

    std::string hello = "Hello from C++";
    return (jboolean) false;
//    return env->NewStringUTF(hello.c_str());
}