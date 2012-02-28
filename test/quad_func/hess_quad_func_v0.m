function H = hess_quad_func_v0(x)
	lambda = 4;
	H = hess_quad_func(x) + lambda*eye(length(x));
end