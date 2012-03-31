function H = hess_exp_func_1(x)
	H = approx_hessian('exp_func_1',x,0.001);
end