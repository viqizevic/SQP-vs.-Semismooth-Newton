function H = hess_paviani_func_v0(x)
	lambda = 0.001;
	H = hess_paviani_func(x) + lambda*eye(length(x));
end