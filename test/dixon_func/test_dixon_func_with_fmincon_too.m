function test_dixon_func_with_fmincon_too()
	lambda = 0.0000000001;
	a = [-10; -10; -10; -10; -10; -10; -10; -10; -10; -10];
	b = [10; 10; 10; 10; 10; 10; 10; 10; 10; 10];
	x0 = [10; 10; 10; 10; 10; 10; 10; 10; 10; 10];
	tol = 0.001;
	itmax = 500;
	[x,fval,it] = semismooth_newton('dixon_func','grad_dixon_func','hess_dixon_func',lambda,a,b,x0,itmax,tol)
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	[x,fval,it] = sqp('dixon_func_v0','grad_dixon_func_v0','hess_dixon_func_v0',A,c,x0,itmax,tol)
	options=optimset('Algorithm','active-set');
	[x,fval] = fmincon('dixon_func_v0',x0,[],[],[],[],a,b,[],options)
end