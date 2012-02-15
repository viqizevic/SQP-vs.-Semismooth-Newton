function g = grad_himmelblau_func_v0(x)
	lambda = 0.000000001;
	g = grad_himmelblau_func(x) + lambda*x;
end