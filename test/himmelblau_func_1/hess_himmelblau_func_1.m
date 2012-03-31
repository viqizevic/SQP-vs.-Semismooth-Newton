function H = hess_himmelblau_func_1(x)
	H = approx_hessian('himmelblau_func_1',x,0.001);
end