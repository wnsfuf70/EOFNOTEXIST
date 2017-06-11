#include <linux/module.h>
#include <linux/gpio.h>
#include <linux/timer.h>
#include <linux/delay.h>
#include <linux/fs.h>
#include <asm/uaccess.h>
#include <linux/ctype.h>
#include <linux/device.h>

#define LEDDRIVE_MAJOR  0
#define LEDDRIVE_NAME   "LEDDRIVE"
#define GPIO5_BASE      0x49056000
#define GPIO_OE_OFFSET          0x34    // ex) DDRA
#define GPIO_DATAIN_OFFSET      0x38    // ex) PINA
#define GPIO_DATAOUT_OFFSET     0x3C    // ex) PORTA
#define USER_LED0               21
#define USER_LED1               22
#define GPIO_OE         (GPIO5_BASE + GPIO_OE_OFFSET)
#define GPIO_DATAIN     (GPIO5_BASE + GPIO_DATAIN_OFFSET)
#define GPIO_DATAOUT    (GPIO5_BASE + GPIO_DATAOUT_OFFSET)

int leddrive_open(struct inode *inode, struct file *filp)
{
    unsigned int *virt_addr;
    printk("Open\n");
    virt_addr = ioremap(GPIO5_BASE, 0x100);
    printk("virtual addr = 0x%x\n",(unsigned int)virt_addr);
    *(virt_addr+GPIO_OE_OFFSET/4) &= ~((1<<USER_LED0) | (1<<USER_LED1));
    return 0;
}

ssize_t leddrive_write(struct file *filp, const char *buf, size_t count, loff_t *f_pos)
{
    char buffer[2];
    unsigned int *virt_addr;
    printk("Write\n");
    virt_addr = ioremap(GPIO5_BASE, 0x100);
    printk("virtual addr = 0x%x\n",(unsigned int)virt_addr);
    copy_from_user(buffer, buf, 2);
    if (buffer[0] == 0)
        *(virt_addr+GPIO_DATAOUT_OFFSET/4) &= ~(1<<USER_LED0);
    else
        *(virt_addr+GPIO_DATAOUT_OFFSET/4) |= (1<<USER_LED0);
    if (buffer[1] == 0)
        *(virt_addr+GPIO_DATAOUT_OFFSET/4) &= ~(1<<USER_LED1);
    else
        *(virt_addr+GPIO_DATAOUT_OFFSET/4) |= (1<<USER_LED1);
    
    return 0;
}

int leddrive_release(struct inode *inode, struct file *filp)
{
    printk("Release\n");
    return 0;
}

struct file_operations leddrive_funcs = {
write: leddrive_write,
open: leddrive_open,
release: leddrive_release
};

int ledDrvMajor = 0; // Major number of driver - will be dynamically given from kernel
int id=0; // minor number

// initialize driver
static int leddrive_init(void)
{
    printk("Initialize\n");
    ledDrvMajor = register_chrdev(ledDrvMajor, LEDDRIVE_NAME, &leddrive_funcs); // register character device driver
    printk("major number=%i\n", ledDrvMajor);
    return 0;
}

static void leddrive_exit(void)
{
    unregister_chrdev(ledDrvMajor, LEDDRIVE_NAME); // unregister character device
    printk("Exit\n");
}

module_init(leddrive_init);
module_exit(leddrive_exit);
