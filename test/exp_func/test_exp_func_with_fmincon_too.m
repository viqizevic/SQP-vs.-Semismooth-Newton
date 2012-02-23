function test_exp_func_with_fmincon_too()
	lambda = 1;
	a = [-10; -2];
	b = [10; 20];
	x0 = [4; 7];
	tol = 0.001;
	itmax = 500;
	[x,it] = semismooth_newton('exp_func','grad_exp_func','hess_exp_func',lambda,a,b,x0,itmax,tol)
	A = [ -eye(length(a)); eye(length(b)) ];
	c = [ -a; b ];
	[x,it] = sqp('exp_func_v0','grad_exp_func_v0','hess_exp_func_v0',A,c,x0,itmax,tol)
	options=optimset('Algorithm','active-set');
	[x,fval] = fmincon('exp_func_v0',x0,[],[],[],[],a,b,[],options)
end