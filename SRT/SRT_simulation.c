#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<Windows.h>
#define listSize 10000
typedef struct Log{
			int turnaroundTime;
			int waitting;					
			int responseTime;
			int getFirstCpuTime;//맨처음시피유를 점유한시간 
			int mari;//switch변수 
}Log;
typedef struct Process{
    
    int pid;
    int arrivalTime;
    int serviceTime;
    /*---------------------*/
    int remainTime;
    char status;
    /*---------------------*/
    Log log;
}Process;
int isFinish(Process* p , int size);
void initList(int* processList);
void showList(int* processList , int currentSize , Process* p , int listLength);
void showProcess(Process* p , int size);
void message(char*);
void report_log(Process* process_list , int CPU_used , int total_time , int size);

int main(int argc , char* argv[]){
    
    int size,i,j,s,tmp,token=0;
    int time=0;
    int Cpu_Idle_time=0;
    int minTime;
    Process* p;
    int processList[listSize];
    int list_length=0;
    FILE* fp = NULL;
    
    if( argc != 2){
        message("Usage: SRTtest.exe <process_file>\n");
        return -1;
    }
    message("SRT 시물레이션을 시작합니다\n"); 
       
    fp = fopen(argv[1],"rt");
    
    fscanf(fp,"%d",&size);
    initList(processList);
    p = (Process*)malloc(sizeof(Process)*size);
    for(i=0; i<size; i++){
        fscanf(fp,"%d %d %d", &p[i].pid , &p[i].arrivalTime , &p[i].serviceTime);
        p[i].remainTime=p[i].serviceTime;
        p[i].status='s';
        p[i].log.waitting=0;
        p[i].log.mari=0;
    }
   /* 입력 끝 */ 
   
    while(!isFinish(p,size)){
																	
							for(i=0; i<size; i++){
       									if(p[i].status=='w' && processList[i]==1){
																	    p[i].log.waitting++;
																}
								}
																				
       for(i=0; i<size; i++){
            if(p[i].status=='r'){
                break;    
            }
            
        }
        
								if(list_length==0){
            printf("시간 : %d  ,  휴유시간 : %d , Cpu 휴유중\n ", time , Cpu_Idle_time);
        }
        else{
            printf("시간 : %d  ,  휴유시간 : %d , Cpu 점유 프로세스 : P%d\n ", time , Cpu_Idle_time,p[i].pid);
        }
        
        /*    프로세스가 도착하는지 확인해서 도착하면 리스트에 넣고 대기상태로 바꿈  */
        for(i=0; i<size; i++){
            if(p[i].arrivalTime==time){
                processList[i]=1;
                list_length++;
                for(j=0; j<size; j++){
                    if(processList[j]==1){
																				     p[j].status='w';
                    }
                }
                
                showList(processList,size,p,list_length);
                showProcess(p,size);
                system("pause");
                    
                   
                /* 리스트에있는 프로세스를 대상으로 우선순를 정한다. */
                minTime=9999999999;
                for(i=0; i<size; i++){
                    if(processList[i] == 1){
                        tmp=p[i].remainTime;
                        if(minTime>tmp){
                            minTime=tmp;
                        }
                    }
                }
                for(i=0; i<size; i++){
                    if(p[i].remainTime==minTime && p[i].status=='w'){
                        p[i].status = 'r';
                    }    
                }
                showList(processList,size,p,list_length);
                showProcess(p ,  size);
                system("pause");
                 /* -------------------------------------------------- */
            
            }
        }
        /*-----------------------------------*/
        if(list_length==0){
            Cpu_Idle_time++;
        }
        /* 실행 상태의 프로세스에게 Cpu를 할당한다... */
        for(i=0; i<size; i++){
								   
            if(p[i].status=='r'){
																if(p[i].log.mari==0){
																    p[i].log.mari=1;
																    p[i].log.getFirstCpuTime=time;
																				//printf("pid : %d , time : %d\n",p[i].pid,time);
																				//system("pause");
																}			
																																	
                p[i].remainTime--;
                if(p[i].remainTime==0){
                    p[i].status='f';
                    processList[i]=0;
                    list_length--;
                    time++; 
                    printf("시간 : %d  ,  휴유시간 : %d\n", time , Cpu_Idle_time);
                    time--;
                				
																				for(j=0; j<size; j++){
                									if(p[j].status=='w'){
																									     p[j].log.waitting++;
																									}
																				}
                				
                    showList(processList,size,p,list_length);
                    showProcess(p,size);
                    system("pause");
                    
                    
                    /* 실행중 프로세스가 끝나면 리스트에있는 프로세스를 대상으로 우선순를 정한다. */
                        minTime=9999999999;
                        for(i=0; i<size; i++){
                            if(processList[i] == 1){
                                tmp=p[i].remainTime;
                                if(minTime>tmp){
                                    minTime=tmp;
                                }
                            }
                        }
                        
                        for(i=0; i<size; i++){
                            if(p[i].remainTime==minTime){
                                p[i].status = 'r';
                            }    
                        }
                        
              						
                        if(isFinish(p,size)==0){
                            showList(processList,size,p,list_length);
                            showProcess(p ,  size);
                            system("pause");
                        }
                        /* -------------------------------------------------- */ 
                    }
            }
            
        }
        /*------------------------------------------------------------*/
        
        time++;
        //리스트에 있고 대기중인 녀석들의 대기시간을 올린다...
								
    }
    
    for(i=0; i<size; i++){
										p[i].log.turnaroundTime = p[i].log.waitting + p[i].serviceTime;
										p[i].log.responseTime = p[i].log.getFirstCpuTime - p[i].arrivalTime;
				}
   
 message("SRT 시물레이션 종료........\n"); 
 report_log(p,time-Cpu_Idle_time,time,size);
 system("pause");
    return 0;
}
void report_log(Process* p , int CPU_used , int total_time , int size){
    int turnaround_sum = 0;
    int waiting_sum = 0;
    int response_sum = 0;
    int i;

    printf("\nLog of Process Scheduling\n");
    for(i=0;i<size;i++) {
        printf("PID(%d)", (p+i)->pid);
        printf("\tTurnaround Time: %d", (p+i)->log.turnaroundTime);
        printf("\tWaiting Time : %d", (p+i)->log.waitting);
        printf("\tResponse Time : %d\n", (p+i)->log.responseTime);
        turnaround_sum += (p+i)->log.turnaroundTime;
        waiting_sum += (p+i)->log.waitting;
        response_sum += (p+i)->log.responseTime;
    }
    printf("\nAverage Turnaround Time: %.2f\n", turnaround_sum / (float)size);
    printf("Average Waiting Time   : %.2f\n", waiting_sum / (float)size);
    printf("Average Response Time  : %.2f\n", response_sum / (float)size);
    printf("CPU Utilization        : %.2f%%\n", (CPU_used / (float)total_time)*100);
}
int isFinish(Process* p , int size){
    int i;
    for(i=0; i<size; i++){
        if(p[i].status!='f'){
            return 0;
        }    
    }
    return 1;
}
void initList(int* processList){
    int i;
    for(i=0; i<listSize; i++){
        processList[i]=0;
    }
    return;   
}
void showList(int* processList , int currentSize , Process* p , int listLength){
    int i;
    printf("-------현재 리스트의상태---------\n");
    if(listLength!=0){ 
        for(i=0; i<currentSize; i++){
            if(*(processList+i)==1){
                printf("pid : %d\n",p[i].pid);    
            }
        }
    }else{
        printf("리스트에 대기중인 프로세스 없음"); 
    }
    printf("\n----------------------------------\n");
    return ;
}
void showProcess(Process* p , int size){   
    int i;
    int sibal;
    for(i=0; i<size; i++){
         printf("pid : %d\n",p[i].pid);
        printf("도착 : %d\t",p[i].arrivalTime);
         printf("서비스 : %d\n",p[i].serviceTime); 
          printf("남은시간 : %d\t", p[i].remainTime);
          printf("대기시간 : %d\n",p[i].log.waitting);
          switch(p[i].status){
            case 's' : printf("상태 : 도착전\n"); break;
            case 'w' : printf("상태 : Waitting\n"); break;
            case 'r' : printf("상태 : Running\n"); break;
            case 'f' : printf("상태 : 프로세스종료\n"); break;
          }
          printf("----------------------------------\n");
    }
    return ;
}
void message(char* msg){
    int i;
    for(i=0; i<strlen(msg); i++){
        printf("%c",msg[i]);
        Sleep(80);
    }
    return;
}  
