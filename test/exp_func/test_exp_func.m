function test_exp_func()
	lambda = 1;
	a = -5;
	b = 5;
	x0 = 4;
	itmax = 10;
	tol = 0.01;
	[x,it] = semismooth_newton('exp_func','grad_exp_func','hess_exp_func',lambda,a,b,x0,itmax,tol)
endfunction