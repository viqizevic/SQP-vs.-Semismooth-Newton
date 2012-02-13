function g = grad_quad_func_v0(x)
	lambda = 1;
	g = grad_quad_func(x) + lambda*x;
end