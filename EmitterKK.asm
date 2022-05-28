	.data
	newline: .asciiz "\n" #newline variable
	varx: .word 0 #initialized as 0
	vary: .word 0 #initialized as 0
	varcount: .word 0 #initialized as 0
	.text
	.globl main
	main: #QTSPIM automatically looks for main
	li $v0 2
	la $t1 varx
	sw $v0, ($t1) #moves the variablex to $t0
	la $t1 varx
	lw $v0 ($t1)
	subu $sp $sp 4
	sw $v0 ($sp) #push on stack
	li $v0 1
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off stack
	addu $v0, $v0, $t0
	la $t1 vary
	sw $v0, ($t1) #moves the variabley to $t0
	la $t1 varx
	lw $v0 ($t1)
	subu $sp $sp 4
	sw $v0 ($sp) #push on stack
	la $t1 vary
	lw $v0 ($t1)
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off stack
	addu $v0, $v0, $t0
	la $t1 varx
	sw $v0, ($t1) #moves the variablex to $t0
	la $t1 varx
	lw $v0 ($t1)
	subu $sp $sp 4
	sw $v0 ($sp) #push on stack
	la $t1 vary
	lw $v0 ($t1)
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off stack
	mult $v0, $t0
	mflo $v0
	move $a0, $v0 #moves $v0 to $a0
	li $v0, 1 #print
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	la $t1 varx
	lw $v0 ($t1)
	subu $sp $sp 4
	sw $v0 ($sp) #push on stack
	la $t1 vary
	lw $v0 ($t1)
	lw $t1 ($sp)
	addu $sp $sp 4 #pop off stack
	ble $t1, $v0, elseif1#condition statement
	#Conditional statement
	la $t1 varx
	lw $v0 ($t1)
	move $a0, $v0 #moves $v0 to $a0
	li $v0, 1 #print
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	la $t1 vary
	lw $v0 ($t1)
	move $a0, $v0 #moves $v0 to $a0
	li $v0, 1 #print
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	j endif1
	elseif1:  #jump for the else
	endif1:	  #jump for the if
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp) #push on stack
	li $v0 14
	lw $t1 ($sp)
	addu $sp $sp 4 #pop off stack
	bne $t1, $v0, elseif2#condition statement
	#Conditional statement
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp) #push on stack
	li $v0 14
	lw $t1 ($sp)
	addu $sp $sp 4 #pop off stack
	beq $t1, $v0, elseif3#condition statement
	#Conditional statement
	li $v0 3
	move $a0, $v0 #moves $v0 to $a0
	li $v0, 1 #print
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	j endif3
	elseif3:  #jump for the else
	endif3:	  #jump for the if
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp) #push on stack
	li $v0 14
	lw $t1 ($sp)
	addu $sp $sp 4 #pop off stack
	bgt $t1, $v0, elseif4#condition statement
	#Conditional statement
	li $v0 4
	move $a0, $v0 #moves $v0 to $a0
	li $v0, 1 #print
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	j endif4
	elseif4:  #jump for the else
	endif4:	  #jump for the if
	j endif2
	elseif2:  #jump for the else
	endif2:	  #jump for the if
	li $v0 15
	subu $sp $sp 4
	sw $v0 ($sp) #push on stack
	li $v0 14
	lw $t1 ($sp)
	addu $sp $sp 4 #pop off stack
	ble $t1, $v0, elseif5#condition statement
	#Conditional statement
	li $v0 5
	move $a0, $v0 #moves $v0 to $a0
	li $v0, 1 #print
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	j endif5
	elseif5:  #jump for the else
	endif5:	  #jump for the if
	li $v0 1
	la $t1 varcount
	sw $v0, ($t1) #moves the variablecount to $t0
	while6:   #jumps for while6
	la $t1 varcount
	lw $v0 ($t1)
	subu $sp $sp 4
	sw $v0 ($sp) #push on stack
	li $v0 15
	lw $t1 ($sp)
	addu $sp $sp 4 #pop off stack
	bgt $t1, $v0, endwhile6#condition statement
	#Conditional statement
	la $t1 varcount
	lw $v0 ($t1)
	move $a0, $v0 #moves $v0 to $a0
	li $v0, 1 #print
	syscall
	la $a0 newline
	li $v0 4
	syscall #print newline
	la $t1 varcount
	lw $v0 ($t1)
	subu $sp $sp 4
	sw $v0 ($sp) #push on stack
	li $v0 1
	lw $t0 ($sp)
	addu $sp $sp 4 #pop off stack
	addu $v0, $v0, $t0
	la $t1 varcount
	sw $v0, ($t1) #moves the variablecount to $t0
	j while6 #loops back to while
	endwhile6: #end of while
	li $v0 10
	syscall #exits
