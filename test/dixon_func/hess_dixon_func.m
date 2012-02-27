function H = hess_dixon_func(x)
	n = 10;
	for k=1:n
		for l=1:n
			H(k,l) = 0;
			if (l == k-1)
				H(k,l) = -4*x(k-1);
			end
			if (l == k)
				H(k,l) = 2+12*x(k)^2;
			end
			if (l == k+1)
				H(k,l) = -4*x(k);
			end
		end
	end
	H(n,n) = 4;
end