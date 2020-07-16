#include <jni.h>
#include <string>
#include <vector>


extern "C"{


typedef struct _JNI_POSREC {
    jclass cls;
    jmethodID constructortorID;
    jfieldID nameID;
    jfieldID idID;
    jfieldID midtermID;
    jfieldID projectID;
    jfieldID finalexamID;
} JNI_POSREC;
 

struct StudentInfo {
    std::string name;
    int id;
    int midterm;
    int project;
    int finalexam;
};

JNI_POSREC * jniPosRec = NULL;
 
/**
*   Fills the Student Record Details.
*/
void FillStudentRecordDetails(std::vector<StudentInfo*>* searchRecordResult ){
    StudentInfo *pRecord1 = new StudentInfo();
    pRecord1->name = "Ram";
    pRecord1->id = 1;
    pRecord1->midterm = 50;
    pRecord1->project = 60;
    pRecord1->finalexam = 70;
    searchRecordResult->push_back(pRecord1);
 
    StudentInfo *pRecord2 = new StudentInfo();
    pRecord2->name = "Raju";
    pRecord2->id = 1;
    pRecord2->midterm = 70;
    pRecord2->project = 80;
    pRecord2->finalexam = 90;
    searchRecordResult->push_back(pRecord2);
}

/**
*   Fills JNI details.
*/
void LoadJniPosRec(JNIEnv * env) {
 
    if (jniPosRec != NULL)
        return;
 
    jniPosRec = new JNI_POSREC;
 
    jniPosRec->cls = env->FindClass("JNISt");
 
    if(jniPosRec->cls != NULL)
        printf("sucessfully created class \n");
 
    jniPosRec->constructortorID = env->GetMethodID(jniPosRec->cls, "<init>", "()V");
    if(jniPosRec->constructortorID != NULL){
        printf("sucessfully created ctorID \n");
    }
 
    jniPosRec->nameID = env->GetFieldID(jniPosRec->cls, "name", "Ljava/lang/String;");
    jniPosRec->idID = env->GetFieldID(jniPosRec->cls, "id", "I");
    jniPosRec->midtermID = env->GetFieldID(jniPosRec->cls, "midterm", "I");
    jniPosRec->projectID = env->GetFieldID(jniPosRec->cls, "project", "I");
    jniPosRec->finalexamID =env->GetFieldID(jniPosRec->cls, "finalexam", "I");
 
}

void FillStudentRecValuesToJni(JNIEnv * env, jobject jPosRec, StudentInfo* cPosRec) {
 
    env->SetObjectField(jPosRec, jniPosRec->nameID, env->NewStringUTF(cPosRec->name.c_str()));
    jint id = (jint)cPosRec->id;
    env->SetIntField(jPosRec, jniPosRec->idID, id);
    jint mid = (jint)cPosRec->midterm;
    env->SetIntField(jPosRec, jniPosRec->midtermID, mid);
    jint pro = (jint)cPosRec->project;
    env->SetIntField(jPosRec, jniPosRec->projectID, pro);
    jint final = (jint)cPosRec->finalexam;
    env->SetIntField(jPosRec, jniPosRec->finalexamID, final);
}

JNIEXPORT jobjectArray JNICALL Java_JNIStudent_getStudent(JNIEnv *env, jclass cls){
    jniPosRec = NULL;
    LoadJniPosRec(env);
    std::vector<StudentInfo*> searchRecordResult ;
    FillStudentRecordDetails(&searchRecordResult);
    printf("\nsearchRecordResult size is"+searchRecordResult.size());
    jobjectArray jPosRecArray = env->NewObjectArray(searchRecordResult.size(), jniPosRec->cls, NULL);
 
    for (size_t i = 0; i < searchRecordResult.size(); i++) {
        jobject jPosRec = env->NewObject(jniPosRec->cls, jniPosRec->constructortorID);
        FillStudentRecValuesToJni(env, jPosRec, searchRecordResult[i]);
        env->SetObjectArrayElement(jPosRecArray, i, jPosRec);
    }
 
    return jPosRecArray;
  }
}