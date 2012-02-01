function test_quad_func()
	lambda = 1;
	a = -10;
	b = 10;
	x0 = 4;
	itmax = 10;
	tol = 0.01;
	[x,it] = semismooth_newton('quad_func','grad_quad_func','hess_quad_func',lambda,a,b,x0,itmax,tol)
endfunction