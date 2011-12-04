// OSL3.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"

#include <stdio.h>
#include <windows.h>

struct h {
	HWND hWnd;
    UINT Msg;
    WPARAM wParam;
    LPARAM lParam;
};
int _tmain(int argc, _TCHAR* argv[])
{
	printf("%d",sizeof(h));
	return 0;
}

