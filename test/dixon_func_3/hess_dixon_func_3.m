function H = hess_dixon_func_3(x)
	n = 10;
	H = approx_hessian('dixon_func_3',x,0.001);
end