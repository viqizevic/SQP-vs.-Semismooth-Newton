function test_quad_func()
	lambda = 1;
	a = -10;
	b = 10;
	x0 = 4;
	itmax = 10;
	tol = 0.01;
	printf "semismooth_newton\n";
	[x,it] = semismooth_newton('quad_func','grad_quad_func','hess_quad_func',lambda,a,b,x0,itmax,tol)
	A = [ -1;
				1 ];
	c = [ -a;
				b ];
	printf "sqp\n";
	[x,it] = sqp('quad_func_v0','grad_quad_func_v0','hess_quad_func_v0',A,c,x0,itmax,tol)
endfunction