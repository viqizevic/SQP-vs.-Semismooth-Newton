function g = grad_dixon_2_dim_func_1(x)
	g = approx_gradient('dixon_2_dim_func_1',x,0.001);
end