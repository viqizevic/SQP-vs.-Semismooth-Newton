function H = hess_dixon_func_4(x)
	n = 10;
	H = approx_hessian('dixon_func_4',x,0.001);
end