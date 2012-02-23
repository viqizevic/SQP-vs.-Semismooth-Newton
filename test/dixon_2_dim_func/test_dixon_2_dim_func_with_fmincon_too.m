function test_dixon_2_dim_func_with_fmincon_too()
	lambda = 0.0000000001;
	a = [-10; -10];
	b = [10; 10];
	x0 = [1.5; 1.5];
	tol = 0.001;
	itmax = 500;
	[x,it] = semismooth_newton('dixon_2_dim_func','grad_dixon_2_dim_func','hess_dixon_2_dim_func',lambda,a,b,x0,itmax,tol)
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	[x,it] = sqp('dixon_2_dim_func_v0','grad_dixon_2_dim_func_v0','hess_dixon_2_dim_func_v0',A,c,x0,itmax,tol)
	options=optimset('Algorithm','active-set');
	[x,fval] = fmincon('dixon_2_dim_func_v0',x0,[],[],[],[],a,b,[],options)
end