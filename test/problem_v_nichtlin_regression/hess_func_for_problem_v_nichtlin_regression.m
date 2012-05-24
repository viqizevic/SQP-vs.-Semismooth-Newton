function H = hess_func_for_problem_v_nichtlin_regression(x)
	eta = [1; 1.1; 1.2; 1.35; 1.55; 1.75; 2.5; 3; 3.7; 4.5];
	xi = [1:1:10]';
	m = length(xi);
	H = zeros(2,2);
	for k=1:m
		H(1,1) = H(1,1) + 2*exp(2*xi(k)*x(2));
	end
	for k=1:m
		H(1,2) = H(1,2) + 4*xi(k)*x(1)*exp(2*xi(k)*x(2)) - 2*xi(k)*eta(k)*exp(xi(k)*x(2));
	end
	H(2,1) = H(1,2);
	for k=1:m
		H(2,2) = H(2,2) + 4*xi(k)^2*x(1)^2*exp(2*xi(k)*x(2)) - 2*xi(k)^2*eta(k)*x(1)*exp(xi(k)*x(2));
	end
end