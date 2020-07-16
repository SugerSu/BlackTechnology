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

using namespace std;

const string path = "D:\\7215 Multi-Thread\\HomeWork9\\GradesC++.txt";

pthread_mutex_t lock1 = PTHREAD_MUTEX_INITIALIZER;

class Student
{
private:
    /* data */
public:
    static int nextID;
    string name;
    int id;
    int midterm;
    int project;
    int finalExam;
    Student(/* args */);
    ~Student();
};
int Student::nextID = 0;

Student::Student(/* args */)
{
    nextID++;
    id = nextID;
    name = "Student" + to_string(id);
}

Student::~Student()
{
}

void write(string data)
{
    ofstream out(path, ios::app);
    if (out.is_open())
    {
        out << data + "\n";
    }
}
list<string> read()
{
    list<string> scores;
    string data;
    ifstream in(path);

    if (!in.is_open())
    {
        cout << "Error opening file";
        exit(1);
    }

    while (!in.eof())
    {
        getline(in, data);
        scores.push_back(data);
        cout << data << endl;
    }
    in.close();
    return scores;
}
void generateGrades(Student *st)
{
    srand((unsigned)time(NULL));
    st->midterm = (rand() % (100 - 50 + 1)) + 50; //[50,100]

    st->project = (rand() % (100 - 50 + 1)) + 50; //[50,100]

    st->finalExam = (rand() % (100 - 50 + 1)) + 50; //[50,100]
}

string calculateGrade(int midTerm, int project, int finalExam)
{
    double totoalScore = 0.3 * midTerm + 0.3 * project + 0.4 * finalExam;
    string score;

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

string toString(Student *st)
{
    return "name," + st->name + ",id," + to_string(st->id) + ",midterm," + to_string(st->midterm) + "," + "project" + "," + to_string(st->project) + "," + "finalExam" + "," + to_string(st->finalExam);
}

list<string> gradeScore(list<string> data)
{

    list<string> res;
    while (!data.empty())
    {
        string score = data.front();
        if (score.length() <= 0)
            break;
        //calculate final grades
        std::stringstream ss(score);
        std::string token;
        vector<string> vec;
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
        string fiscore = calculateGrade(midterm, project, finalexam);
        vec.push_back("FinalScore,");
        vec.push_back(fiscore);
        res.push_back(score + ",FinalScore," + fiscore);
        data.pop_front();
    }

    return res;
}

void studentThread()
{
    Student *st = new Student();
    srand((unsigned)time(NULL));
    st->midterm = (rand() % (100 - 50 + 1)) + 50;   //[50,100]
    st->project = (rand() % (100 - 50 + 1)) + 50;   //[50,100]
    st->finalExam = (rand() % (100 - 50 + 1)) + 50; //[50,100]
    //generateGrades(st);
    Sleep(1000);
    //string d = toString(st);
    string d = "name," + st->name + ",id," + to_string(st->id) + ",midterm," + to_string(st->midterm) + "," + "project" + "," + to_string(st->project) + "," + "finalExam" + "," + to_string(st->finalExam);
    delete st;
    pthread_mutex_lock(&lock1);
    //write(d);
    ofstream out(path, ios::app);
    if (out.is_open())
    {
        out << d + "\n";
    }
    pthread_mutex_unlock(&lock1);
}
void graderThread()
{
    list<string> data = read();
    list<string> score = gradeScore(data);
    pthread_mutex_lock(&lock1);
    while (!score.empty())
    {
        write(score.front());
        score.pop_front();
    }
    pthread_mutex_unlock(&lock1);
}
int main(int argc, char const *argv[])
{
    /* code */
    //test average time
    // long time = 0;
    // for (int i = 0; i < 10; i++)
    // {
    //     time_t c_start, c_end;
    //     c_start = clock();
    //     cout << c_start << endl;
    //     std::vector<std::thread> threads;
    //     for (int i = 1; i <= 1; ++i)
    //     {

    //         threads.push_back(std::thread(studentThread));
    //     }
    //     for (auto &thr : threads)
    //     {
    //         thr.join();
    //     }

    //     c_end = clock();
    //     cout << c_start << endl;
    //     printf("Creating threads used %f ms by time().", difftime(c_end, c_start)); //1154-1008
    //     cout << endl;
    //     ;
    //     time += difftime(c_end, c_start);
    // }
    // cout << "average :" << time / 10 << endl;
    time_t c_start, c_end;
    c_start = clock();
    cout << c_start << endl;
    // pthread_t threads[150], grader;
    // for (int i = 0; i < 150; i++)
    // {
    //     /* code */
    //     int iret = pthread_create(&threads[i], NULL, studentThread, NULL);
    // }
    // c_end = clock();
    // printf("Creating threads used %f ms by time()./n",difftime(c_end,c_start)) ;
    std::thread t(studentThread);
    t.join();
    std::vector<std::thread> threads;
    for (int i = 1; i <= 1; ++i)
    {
        threads.push_back(std::thread(studentThread));
    }
    for (auto &thr : threads)
    {
        thr.join();
    }

    c_end = clock();
    cout << c_start << endl;
    printf("Creating threads used %f ms by time()./n", difftime(c_end, c_start));

    Sleep(30000);

    std::thread t1(graderThread);
    t1.join();
    exit(EXIT_SUCCESS);
}
