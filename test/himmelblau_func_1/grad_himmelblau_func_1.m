function g = grad_himmelblau_func_1(x)
	g = approx_gradient('himmelblau_func_1',x,0.001);
end