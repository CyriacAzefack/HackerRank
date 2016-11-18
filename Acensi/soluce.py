#!/usr/bin/python
# Conway's game of life - set up diagonals across the board
BOARD_SIZE = 28;

#get the next move, 
def nextMove(player, board):
    b = [] #append to b
    for s in board:
        b.append(list(s))

    #check up left (rows) 
    for r in range(0, BOARD_SIZE, 2):
        if (b[BOARD_SIZE - r][BOARD_SIZE] == "-"):
            return BOARD_SIZE - r, BOARD_SIZE
        else:
            row, col= diagUL(b, BOARD_SIZE - r, BOARD_SIZE)
            if (row!= -1):
                return row, col

    #check up left (cols)
    for c in range(0, BOARD_SIZE, 2):
        if (b[BOARD_SIZE][BOARD_SIZE - c] == "-"):
            return BOARD_SIZE, BOARD_SIZE - c
        else:
            row, col= diagUL(b, BOARD_SIZE, BOARD_SIZE - c)
            if (row!= -1):
                return row, col

    #check up right (rows) 
    for r in range(0, BOARD_SIZE, 2):
        if (b[r][BOARD_SIZE] == "-"):
            return r, BOARD_SIZE
        else:
            row, col= diagUR(b, r, BOARD_SIZE)
            if (row!= -1):
                return row, col

    #check up right (cols)
    for c in range(0, BOARD_SIZE, 2):
        if (b[0][BOARD_SIZE - c] == "-"):
            return 0, BOARD_SIZE - c
        else:
            row, col= diagUR(b, 0, BOARD_SIZE - c)
            if (row!= -1):
                return row, col

#gets the diagonals (up right and up left) 
def diagUL(bd, r, c):
    if (r == 0 or c == 0):
        return -1, -1
    elif (bd[r - 1][c - 1] == "-"):
        return r - 1, c - 1
    else:
        return diagUL(bd, r - 1, c - 1)

def diagUR(bd, r, c):
    if (r == BOARD_SIZE or c == 0):
        return -1, -1
    elif (bd[r + 1][c - 1] == "-"):
        return r + 1, c - 1
    else:
        return diagUL(bd, x + 1, y - 1)

#i/o
player = raw_input()
board = []
for row in xrange(0, 29):
    board.append(raw_input())
a,b = nextMove(player, board)
print a,b