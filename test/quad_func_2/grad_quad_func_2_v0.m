function g = grad_quad_func_2_v0(x)
	lambda = 1;
	g = grad_quad_func_2(x) + lambda*x;
end