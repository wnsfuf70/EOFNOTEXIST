#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
int main()
{       volatile int    dev, i;
        volatile char   buff[2];
        printf("OPEN Start\n");
        dev = open("/dev/leddrive", O_WRONLY);
        printf("OPEN End\n");
        printf("WRITE Start\n");
        /*for (i=0; i<400000; i++)
        {
                buff[1] = (i/100000)%2;
                buff[0] = (i/200000)%2;
                write(dev, &buff, 2);
        }*/
        for (i=0; i<(400000/5); i++)
        {
                buff[1] = (i/(100000/5))%2;
                buff[0] = (i/(40000/5))%2;
                write(dev, &buff, 2);
        }
        printf("WRITE End\n");
        printf("RELESE Start\n");
        close(dev);
        printf("RELESE End\n");
        return 0;
}
