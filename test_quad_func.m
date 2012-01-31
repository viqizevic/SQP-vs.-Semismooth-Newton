function test_quad_func()
	lambda = 2;
	a = -1000;
	b = 1000;
	x0 = 10;
	itmax = 100;
	tol = 0.01;
	[x,it] = semismooth_newton('quad_func','grad_quad_func','hess_quad_func',lambda,a,b,x0,itmax,tol)
endfunction