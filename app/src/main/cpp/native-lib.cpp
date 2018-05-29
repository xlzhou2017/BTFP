#include <jni.h>
#include <string>
#include <fcntl.h>
#include <stdio.h>
#include <sys/stat.h>
#include <sys/ioctl.h>
#include <unistd.h>
#include <stdlib.h>
#include <errno.h>
#include <android/log.h>

#define DEVNAME  "/dev/EZ-USB0"
#define DEVNAME1  "/dev/EZ-USB1"
#define DEVNAME2  "/dev/EZ-USB2"
#define DEVNAME3  "/dev/EZ-USB3"
#define cMotSM  0
#define cMotGP  1
#define cMotJP  2
#define cMotJN  3
#define cMotHO  4
#define cMotRD  5
#define cMotCNT 6

#define cmd_son 1
#define cmd_des 2
#define cmd_sp  3
#define cmd_mot 4
#define cmd_pos 5
#define cmd_dir 6

#define ca0Addr 129
#define ca1Addr 130
#define ca4Addr 131
#define ca5Addr 132
#define ca6Addr 134
#define ca7Addr 135

#define LOG_TAG "native-lib"
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

extern "C"
{
static char CBuf[4];
static char AddrA0[2];
static char AddrA4[2];
static char AddrA5[2];

jstring
Java_com_hdl_btfp_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
 jint Java_com_hdl_btfp_Fpga_SetDo(JNIEnv *env, jobject obj ,jint dev, jint add, jint cmd)
{
    int fd = 0;
    CBuf[0] = 0;
    CBuf[1] = (char)add;
    CBuf[2] = 0;
    CBuf[3] = (char)cmd;

    switch (dev)
    {
        case 0:
            if((fd=open(DEVNAME ,O_RDWR))<0)
            {
                LOGI("setDo_dev_0");
                return -EFAULT;
            }
            break;
        case 1:
            if((fd=open(DEVNAME1 ,O_RDWR))<0)
            {
                LOGI("setDo_dev_1");
                return -EFAULT;
            }
            break;
        case 2:
            if((fd=open(DEVNAME2 ,O_RDWR))<0)
            {
                LOGI("setDo_dev_2");
                return -EFAULT;
            }
            break;
        case 3:
            if((fd=open(DEVNAME3 ,O_RDWR))<0)
            {
                LOGI("setDo_dev_3");
                return -EFAULT;
            }
            break;
        default:
            LOGI("setDo_dev_no");
            break;
    }
    if((write(fd,CBuf, sizeof(CBuf)))<0)
    {
        return -EFAULT;
    }
    close(fd);
    return 0;
}
jint Java_com_hdl_btfp_Fpga_GetDi(JNIEnv *env, jobject obj ,jint dev,jint add)
{
    int fd = 0;
    char RBuf[4];
    switch (dev)
    {
        case 0:
            if((fd=open(DEVNAME ,O_RDWR))<0)
            {
                    return -EFAULT;
            }
            break;
        case 1:
            if((fd=open(DEVNAME1 ,O_RDWR))<0)
            {
                    return -EFAULT;
            }
            break;
        case 2:
            if((fd=open(DEVNAME2 ,O_RDWR))<0)
            {
                    return -EFAULT;
            }
            break;
        case 3:
            if((fd=open(DEVNAME3 ,O_RDWR))<0)
            {
                    return -EFAULT;
            }
            break;
        default:
            LOGI("getDi_dev_no");
            break;
    }
    if((read(fd,RBuf, (size_t)(add+1)))<0)
    {
        return -EFAULT;
    }
    close(fd);
    return RBuf[3];

}
/*
 jint Java_com_hdl_btfp_Fpga_GetDi(JNIEnv *env, jobject obj ,jint dev,jint add)
 {
     int fd = 0;
     char RBuf[4];
     switch (dev)
     {
         case 0:
             if((fd=open(DEVNAME ,O_RDWR))<0)
             {
                 return -EFAULT;
             }
             else
             {
              //   LOGI("getDi_dev_0");
             }
             break;
         case 1:
             if((fd=open(DEVNAME1 ,O_RDWR))<0)
             {
                 return -EFAULT;
             }
             else
             {
                // LOGI("getDi_dev_1");
             }
             break;
         case 2:
             if((fd=open(DEVNAME2 ,O_RDWR))<0)
             {
                 return -EFAULT;
             }
             else
             {
                // LOGI("getDi_dev_2");
             }
             break;
         case 3:
             if((fd=open(DEVNAME3 ,O_RDWR))<0)
             {
                 return -EFAULT;
             }
             else
             {
                // LOGI("getDi_dev_3");
             }
             break;
         default:
             LOGI("getDi_dev_no");
             break;
     }
     if((read(fd,RBuf, (size_t)(add+1)))<0)
     {
         return -EFAULT;
     }
     close(fd);
     return RBuf[3];
 }*/
 jint Java_com_hdl_btfp_Fpga_SetSp(JNIEnv *env, jobject obj , jint dev, jint id, jint sp)
 {
     int fd = 0;
    short spl,sph;
     spl = (short)(0xFFFF & sp);
     sph = (short)(0xFFFF & (sp >> 16));
     CBuf[0] = 0;
     CBuf[1] = AddrA4[id];
     CBuf[2] =(char)(0xFF & (spl >> 8));
     CBuf[3] =(char)((0xFF & spl));

     switch (dev)
     {
         case 0:
             if((fd=open(DEVNAME ,O_RDWR))<0)
             {
                 LOGI("setSp_dev_0");
                 return -EFAULT;
             }
             break;
         case 1:
             if((fd=open(DEVNAME1 ,O_RDWR))<0)
             {
                 LOGI("setSp_dev_1");
                 return -EFAULT;
             }
             break;
         case 2:
             if((fd=open(DEVNAME2 ,O_RDWR))<0)
             {
                 LOGI("setSp_dev_2");
                 return -EFAULT;
             }
             break;
         case 3:
             if((fd=open(DEVNAME3 ,O_RDWR))<0)
             {
                 LOGI("setSp_dev_3");
                 return -EFAULT;
             }
             break;
         default:
             break;
     }
     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }
     CBuf[0] = 0;
     CBuf[1] = AddrA5[id];
     CBuf[2] =(char)(0xFF & (sph >> 8));
     CBuf[3] = (char)(0xFF & sph);

     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }

     CBuf[0] = 0;
     CBuf[1] = AddrA0[id];
     CBuf[2] = 0;
     CBuf[3] = cmd_sp;

     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }
     close(fd);
     return 0;
 }
 jint Java_com_hdl_btfp_Fpga_MotorJP(JNIEnv *env, jobject obj ,jint dev, jint id)
 {
     int fd = 0 ;
     CBuf[0] = 0;
     CBuf[1] = AddrA4[id];
     CBuf[2] = 0;
     CBuf[3] = cMotRD;

     switch (dev)
     {
         case 0:
             if((fd=open(DEVNAME ,O_RDWR))<0)
             {
                 LOGI("motorJp_dev_0");
                 return -EFAULT;
             }
             break;
         case 1:
             if((fd=open(DEVNAME1 ,O_RDWR))<0)
             {
                 LOGI("motorJp_dev_1");
                 return -EFAULT;
             }
             break;
         case 2:
             if((fd=open(DEVNAME2 ,O_RDWR))<0)
             {
                 LOGI("motorJp_dev_2");
                 return -EFAULT;
             }
             break;
         case 3:
             if((fd=open(DEVNAME3 ,O_RDWR))<0)
             {
                 LOGI("motorJp_dev_3");
                 return -EFAULT;
             }
             break;
         default:
             break;
     }
     if((write(fd,CBuf, sizeof(CBuf)))<0)
         {
         return -EFAULT;
     }

     CBuf[0] = 0;
     CBuf[1] = AddrA0[id];
     CBuf[2] = 0;
     CBuf[3] = cmd_mot;
     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }

     CBuf[0] = 0;
     CBuf[1] = AddrA4[id];
     CBuf[2] = 0;
     CBuf[3] = cMotJP;
     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }

     CBuf[0] = 0;
     CBuf[1] = AddrA0[id];
     CBuf[2] = 0;
     CBuf[3] = cmd_mot;
     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }
     close(fd);
     return 0;
 }
 jint Java_com_hdl_btfp_Fpga_MotorJN(JNIEnv *env, jobject obj ,jint dev, jint id)
 {
     int fd = 0;
     CBuf[0] = 0;
     CBuf[1] = AddrA4[id];
     CBuf[2] = 0;
     CBuf[3] = cMotRD;

     switch (dev)
     {
         case 0:
             if((fd=open(DEVNAME ,O_RDWR))<0)
             {
                 LOGI("motorJn_dev_0");
                 return -EFAULT;
             }
             break;
         case 1:
             if((fd=open(DEVNAME1 ,O_RDWR))<0)
             {
                 LOGI("motorJn_dev_1");
                 return -EFAULT;
             }
             break;
         case 2:
             if((fd=open(DEVNAME2 ,O_RDWR))<0)
             {
                 LOGI("motorJn_dev_2");
                 return -EFAULT;
             }
             break;
         case 3:
             if((fd=open(DEVNAME3 ,O_RDWR))<0)
             {
                 LOGI("motorJn_dev_3");
                 return -EFAULT;
             }
             break;
         default:
             break;
     }
     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }

     CBuf[0] = 0;
     CBuf[1] = AddrA0[id];
     CBuf[2] = 0;
     CBuf[3] = cmd_mot;
     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }

     CBuf[0] = 0;
     CBuf[1] = AddrA4[id];
     CBuf[2] = 0;
     CBuf[3] = cMotJN;
     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }

     CBuf[0] = 0;
     CBuf[1] = AddrA0[id];
     CBuf[2] = 0;
     CBuf[3] = cmd_mot;
     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }
     close(fd);
     return 0;
 }
jint Java_com_hdl_btfp_Fpga_MotorGoHome(JNIEnv *env, jobject obj ,jint dev, jint id)
{
    int fd = 0;
    CBuf[0] = 0;
    CBuf[1] = AddrA4[id];
    CBuf[2] = 0;
    CBuf[3] = cMotRD;

    switch (dev)
    {
        case 0:
            if((fd=open(DEVNAME ,O_RDWR))<0)
            {
                LOGI("motorJn_dev_0");
                return -EFAULT;
            }
            break;
        case 1:
            if((fd=open(DEVNAME1 ,O_RDWR))<0)
            {
                LOGI("motorJn_dev_1");
                return -EFAULT;
            }
            break;
        case 2:
            if((fd=open(DEVNAME2 ,O_RDWR))<0)
            {
                LOGI("motorJn_dev_2");
                return -EFAULT;
            }
            break;
        case 3:
            if((fd=open(DEVNAME3 ,O_RDWR))<0)
            {
                LOGI("motorJn_dev_3");
                return -EFAULT;
            }
            break;
        default:
            break;
    }
    if((write(fd,CBuf, sizeof(CBuf)))<0)
    {
        return -EFAULT;
    }

    CBuf[0] = 0;
    CBuf[1] = AddrA0[id];
    CBuf[2] = 0;
    CBuf[3] = cmd_mot;
    if((write(fd,CBuf, sizeof(CBuf)))<0)
    {
        return -EFAULT;
    }

    CBuf[0] = 0;
    CBuf[1] = AddrA4[id];
    CBuf[2] = 0;
    CBuf[3] = cMotHO;
    if((write(fd,CBuf, sizeof(CBuf)))<0)
    {
        return -EFAULT;
    }

    CBuf[0] = 0;
    CBuf[1] = AddrA0[id];
    CBuf[2] = 0;
    CBuf[3] = cmd_mot;
    if((write(fd,CBuf, sizeof(CBuf)))<0)
    {
        return -EFAULT;
    }
    close(fd);
    return 0;
}
 jint Java_com_hdl_btfp_Fpga_MotorStop(JNIEnv *env, jobject obj , jint dev, jint id)
 {
     int fd = 0;
     CBuf[0] = 0;
     CBuf[1] = AddrA4[id];
     CBuf[2] = 0;
     CBuf[3] = cMotRD;

     switch (dev)
     {
         case 0:
             if((fd=open(DEVNAME ,O_RDWR))<0)
             {
                 return -EFAULT;
             }
             break;
         case 1:
             if((fd=open(DEVNAME1 ,O_RDWR))<0)
             {
                 return -EFAULT;
             }
             break;
         case 2:
             if((fd=open(DEVNAME2 ,O_RDWR))<0)
             {
                 return -EFAULT;
             }
             break;
         case 3:
             if((fd=open(DEVNAME3 ,O_RDWR))<0)
             {
                 return -EFAULT;
             }
             break;
         default:
             break;
     }
     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }

     CBuf[0] = 0;
     CBuf[1] = AddrA0[id];
     CBuf[2] = 0;
     CBuf[3] = cmd_mot;
     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }

     CBuf[0] = 0;
     CBuf[1] = AddrA4[id];
     CBuf[2] = 0;
     CBuf[3] = cMotSM;
     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }

     CBuf[0] = 0;
     CBuf[1] = AddrA0[id];
     CBuf[2] = 0;
     CBuf[3] = cmd_mot;
     if((write(fd,CBuf, sizeof(CBuf)))<0)
     {
         return -EFAULT;
     }
     close(fd);
     return 0;
 }

jint Java_com_hdl_btfp_Fpga_MotPtp(JNIEnv *env, jobject obj ,jint dev, jint id, jint pos)
{
    int fd = 0 ;
    u_short posl;
    u_short posh;

    switch (dev)
    {
        case 0:
            if((fd=open(DEVNAME ,O_RDWR))<0)
            {
                LOGI("motorJp_dev_0");
                return -EFAULT;
            }
            break;
        case 1:
            if((fd=open(DEVNAME1 ,O_RDWR))<0)
            {
                LOGI("motorJp_dev_1");
                return -EFAULT;
            }
            break;
        case 2:
            if((fd=open(DEVNAME2 ,O_RDWR))<0)
            {
                LOGI("motorJp_dev_2");
                return -EFAULT;
            }
            break;
        case 3:
            if((fd=open(DEVNAME3 ,O_RDWR))<0)
            {
                LOGI("motorJp_dev_3");
                return -EFAULT;
            }
            break;
        default:
            break;
    }
    posl = (u_short)(0xFFFF & pos);
    posh = (u_short)(0xFFFF & (pos >> 16));
    CBuf[0] = 0;
    CBuf[1] = AddrA4[id];
    CBuf[2] = (char)(0xFF & (posl >> 8));
    CBuf[3] = (char)(0xFF & posl);

    if((write(fd,CBuf, sizeof(CBuf)))<0)
    {
        return -EFAULT;
    }

    CBuf[0] = 0;
    CBuf[1] = AddrA5[id];
    CBuf[2] = (char)(0xFF & (posh >> 8));
    CBuf[3] = (char)(0xFF & posh);
    if((write(fd,CBuf, sizeof(CBuf)))<0)
    {
        return -EFAULT;
    }

    CBuf[0] = 0;
    CBuf[1] = AddrA0[id];
    CBuf[2] = 0;
    CBuf[3] = cmd_des;
    if((write(fd,CBuf, sizeof(CBuf)))<0)
    {
        return -EFAULT;
    }

    CBuf[0] = 0;
    CBuf[1] = AddrA4[id];
    CBuf[2] = 0;
    CBuf[3] = cMotRD;
    if((write(fd,CBuf, sizeof(CBuf)))<0) {
        return -EFAULT;
    }

    CBuf[0] = 0;
    CBuf[1] = AddrA0[id];
    CBuf[2] = 0;
    CBuf[3] = cmd_mot;
    if((write(fd,CBuf, sizeof(CBuf)))<0)
    {
        return -EFAULT;
    }

    CBuf[0] = 0;
    CBuf[1] = AddrA4[id];
    CBuf[2] = 0;
    CBuf[3] = cMotGP;
    if((write(fd,CBuf, sizeof(CBuf)))<0)
    {
        return -EFAULT;
    }

    CBuf[0] = 0;
    CBuf[1] = AddrA0[id];
    CBuf[2] = 0;
    CBuf[3] = cmd_mot;
    if((write(fd,CBuf, sizeof(CBuf)))<0)
    {
        return -EFAULT;
    }

    close(fd);
    return 0;
}

void Java_com_hdl_btfp_Fpga_InitAddr(JNIEnv *env, jobject obj )
{
    AddrA0[0] = (char)ca0Addr;
    AddrA0[1] = (char)(ca0Addr + 32);
    AddrA4[0] = (char)ca4Addr;
    AddrA4[1] = (char)(ca4Addr + 32);
    AddrA5[0] = (char)ca5Addr;
    AddrA5[1] = (char)(ca5Addr + 32);

}
}