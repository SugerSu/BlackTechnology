#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>


using namespace std;

pthread_mutex_t mutex1 = PTHREAD_MUTEX_INITIALIZER;

int counter = 0;

void *print_message_function(void *ptr);

int main()
{

    pthread_t thread1, thread2;

    int iret1, iret2;


    iret1 = pthread_create(&thread1, NULL, print_message_function, NULL);

    if (iret1)
    {

        fprintf(stderr, "Error - pthread_create() return code: %d\n", iret1);

        exit(EXIT_FAILURE);
    }

    iret2 = pthread_create(&thread2, NULL, print_message_function, NULL);

    if (iret2)
    {

        fprintf(stderr, "Error - pthread_create() return code: %d\n", iret2);

        exit(EXIT_FAILURE);
    }

    printf("pthread_create() for thread 1 returns: %d\n", iret1);

    printf("pthread_create() for thread 2 returns: %d\n", iret2);

    pthread_join(thread1, NULL);

    pthread_join(thread2, NULL);
    //terminate the process including any threads
    exit(EXIT_SUCCESS);
    

}

void *print_message_function(void *ptr)
{

    pthread_mutex_lock(&mutex1);
    counter++;

    printf("Counter value: %d\n", counter);

    pthread_mutex_unlock(&mutex1);
}



