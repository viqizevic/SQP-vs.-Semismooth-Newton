function test_quad_func_with_fmincon_too()
	lambda = 1;
	a = [-10];
	b = [10;
	x0 = [40];
	tol = 0.001;
	itmax = 100;
	[x,it] = semismooth_newton('quad_func','grad_quad_func','hess_quad_func',lambda,a,b,x0,itmax,tol)
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	[x,it] = sqp('quad_func_v0','grad_quad_func_v0','hess_quad_func_v0',A,c,x0,itmax,tol)
	options=optimset('Algorithm','active-set');
	[x,fval] = fmincon('quad_func_v0',x0,[],[],[],[],a,b,[],options)
end