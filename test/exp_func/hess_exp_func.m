function H = hess_exp_func(x)
	n = length(x);
	efx = exp_func(x);
	H = 2*efx*eye(n);
	for k=1:n
		for l=1:n
			H(k,l) += 4*efx*x(k)*x(l);
		endfor
	endfor
endfunction