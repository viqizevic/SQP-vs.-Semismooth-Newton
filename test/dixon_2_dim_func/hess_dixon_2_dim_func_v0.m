function H = hess_dixon_2_dim_func_v0(x)
	lambda = 0.00001;
	H = hess_dixon_2_dim_func(x) + lambda*eye(length(x));
end