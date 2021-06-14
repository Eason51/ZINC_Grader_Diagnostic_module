#include <unistd.h>
#include <sys/wait.h>
#include <string>
#include <iostream>

using namespace std;

const int BATCH_AMOUNT = 100;

int main(){
    for(int i = 0; i != BATCH_AMOUNT; ++i)
    {
        char* commandArr[4];
        char programName[] = "python3";
        char fileName[] = "filter.py";
        commandArr[0] = programName;
        commandArr[1] = fileName;
        commandArr[3] = NULL;

        string batch = to_string(i);
        commandArr[2] = new char[batch.length() + 1];
        batch.copy(commandArr[2], batch.length());

        pid_t pid;
        pid = fork();
        if(pid == 0)
            execvp("python3", commandArr);
        else
            continue;
    }

    pid_t waitId;
    int status = 0;
    while((waitId = wait(&status)) > 0);

    cout << "task completed" << endl;
    exit(0);
}