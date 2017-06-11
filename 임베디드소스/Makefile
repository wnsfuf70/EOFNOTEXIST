MODULES = leddrive
KERNEL_SOURCE = /root/stable-kernel-3.2.33-x15/KERNEL
export ARCH = arm
CURDIR = /root/leddrive
export CROSS_COMPILE = arm-linux-gnueabi-
CFLAGS =
default : all
obj-m += $(MODULES:%=%.o)
BUILD = $(MODULES:%=%.ko)
all :: $(BUILD)
clean:
	rm -f $(BUILD) *.o *.ko *.mod.c *.mod.o *~ *.cmd Module.symvers modules.order
	rm -rf .tmp_versions
$(MODULES:%=%.ko) : *.c
	$(MAKE) CROSS_COMFILE=$(CROSS_COMPILE) ARCH=$(ARCH) -C $(KERNEL_SOURCE) SUBDIRS=$(CURDIR) M=$(CURDIR) modules

