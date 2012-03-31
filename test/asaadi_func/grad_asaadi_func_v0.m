function g = grad_asaadi_func_v0(x)
	lambda = 0.0001;
	g = grad_asaadi_func(x) + lambda*x;
end