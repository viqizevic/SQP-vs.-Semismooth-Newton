function g = grad_dixon_3_dim_func_v0(x)
	lambda = 0.0000000001;
	g = grad_dixon_3_dim_func(x) + lambda*x;
end