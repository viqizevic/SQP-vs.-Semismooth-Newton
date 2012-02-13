function H = hess_quad_func_v0(x)
	lambda = 1;
	H = hess_quad_func(x) + lambda*eye(length(x));
end