function H = hess_dixon_2_dim_func_1(x)
	H = approx_hessian('dixon_2_dim_func_1',x,0.001);
end