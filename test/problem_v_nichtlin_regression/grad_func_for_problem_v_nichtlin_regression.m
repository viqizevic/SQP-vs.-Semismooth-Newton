function g = grad_func_for_problem_v_nichtlin_regression(x)
	xi = [1:1:10]';
	eta = [1; 1.1; 1.2; 1.35; 1.55; 1.75; 2.5; 3; 3.7; 4.5];
	m = length(xi);
	g = zeros(2,1);
   	for k=1:m
		g(1) = g(1) + 2*exp(xi(k)*x(2)) * ( x(1)*exp(xi(k)*x(2)) - eta(k) );
	end
   	for k=1:m
		g(2) = g(2) + 2*xi(k)*x(1)*exp(xi(k)*x(2)) * ( x(1)*exp(xi(k)*x(2)) - eta(k) );
	end
end