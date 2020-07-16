#include <jni.h>
#include <string>
#include <vector>
#include <iostream>
#include <string>
#include <fstream>
#include <list>
#include <stdlib.h>
#include <condition_variable>
#include <mutex>
#include <thread>
#include <stdio.h>
#include <string.h>
#include <windows.h>
#include <sstream>
#include <algorithm>
#include <iterator>
#include <vector>

extern "C"
{

  const std::string path = "D:\\7215 Multi-Thread\\HomeWork9\\GradesC++.txt";

  pthread_mutex_t lock1 = PTHREAD_MUTEX_INITIALIZER;

  
  struct StudentInfo
  {
    //std::string name;
    int id;
    int midterm;
    int project;
    int finalexam;
  };
  
  std::string calculateGrade(int midTerm, int project, int finalExam)
  {
    double totoalScore = 0.3 * midTerm + 0.3 * project + 0.4 * finalExam;
    std::string score;

    if (totoalScore > 90)
      score = "A";
    else if (totoalScore > 80)
      score = "B";
    else if (totoalScore > 70)
      score = "C";
    else if (totoalScore > 60)
      score = "D";
    else
      score = "E";

    return score;
  }

  std::string toString(StudentInfo *st)
  {
    return "id," + std::to_string(st->id) + ",midterm," + std::to_string(st->midterm) + "," + "project" + "," + std::to_string(st->project) + "," + "finalExam" + "," + std::to_string(st->finalexam);
  }

  std::list<std::string> gradeScore(std::list<std::string> data)
  {

    std::list<std::string> res;
    while (!data.empty())
    {
      std::string score = data.front();
      if (score.length() <= 0)
        break;
      //calculate final grades
      std::stringstream ss(score);
      std::string token;
      std::vector<std::string> vec;
      int midterm = 0;
      int project = 0;
      int finalexam = 0;
      while (std::getline(ss, token, ','))
      {
        vec.push_back(token);
      }
      midterm = std::stoi(vec[5]);
      project = std::stoi(vec[7]);
      finalexam = std::stoi(vec[9]);
      std::string fiscore = calculateGrade(midterm, project, finalexam);
      vec.push_back("FinalScore,");
      vec.push_back(fiscore);
      res.push_back(score + ",FinalScore," + fiscore);
      data.pop_front();
    }

    return res;
  }

  void studentThread()
  {
    StudentInfo *st = new StudentInfo();
    srand((unsigned)time(NULL));
    st->id=(rand() % (100 ));
    st->midterm = (rand() % (100 - 50 + 1)) + 50;   //[50,100]
    st->project = (rand() % (100 - 50 + 1)) + 50;   //[50,100]
    st->finalexam = (rand() % (100 - 50 + 1)) + 50; //[50,100]

    std::string d = ",id," + std::to_string(st->id) + ",midterm," + std::to_string(st->midterm) + "," + "project" + "," + std::to_string(st->project) + "," + "finalExam" + "," + std::to_string(st->finalexam);
    delete st;
    pthread_mutex_lock(&lock1);

    std::ofstream out(path, std::ios::app);
    if (out.is_open())
    {
      out << d + "\n";
    }
    pthread_mutex_unlock(&lock1);
  }

  void graderThread()
  {
    std::list<std::string> data = read();
    std::list<std::string> score = gradeScore(data);
    pthread_mutex_lock(&lock1);
    while (!score.empty())
    {
      write(score.front());
      score.pop_front();
    }
    pthread_mutex_unlock(&lock1);
  }

  void write(std::string data)
  {
    std::ofstream out(path, std::ios::app);
    if (out.is_open())
    {
      out << data + "\n";
    }
  }
  std::list<std::string> read()
  {
    std::list<std::string> scores;
    std::string data;
    std::ifstream in(path);

    if (!in.is_open())
    {
      std::cout << "Error opening file";
      exit(1);
    }

    while (!in.eof())
    {
      getline(in, data);
      scores.push_back(data);
      std::cout << data << std::endl;
    }
    in.close();
    return scores;
  }

  /*
 * Class:     JNIStudent
 * Method:    getScores
 * Signature: ()[Ljava/lang/String;
 */
  JNIEXPORT void JNICALL Java_JNIThread_jniThreads(JNIEnv *env, jclass cls)
  {
    std::thread t(studentThread);
    t.join();
    std::vector<std::thread> threads;
    for (int i = 1; i <= 5; ++i)
    {
      threads.push_back(std::thread(studentThread));
    }
    for (auto &thr : threads)
    {
      thr.join();
    }

    std::thread t1(graderThread);
    t1.join();
  }
}