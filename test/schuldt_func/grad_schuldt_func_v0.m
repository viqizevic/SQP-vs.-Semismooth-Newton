function g = grad_schuldt_func_v0(x)
	lambda = 0.03;
	g = grad_schuldt_func(x) + lambda*x;
end