function g = grad_paviani_func_v0(x)
	lambda = 0.001;
	g = grad_paviani_func(x) + lambda*x;
end