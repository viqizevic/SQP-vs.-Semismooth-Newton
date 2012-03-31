function g = grad_dixon_2_dim_func_1_v0(x)
	lambda = 0.001;
	g = grad_dixon_2_dim_func_1(x) + lambda*x;
end