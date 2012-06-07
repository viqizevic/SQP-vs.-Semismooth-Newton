%problem_v_nichtlin_regression
%\min_{x\in\R^2}\ f(x) %TODO write f(x)
%% y = 0;
%% 	for k=1:m
%% 		y = y + ( x(1)*exp(xi(k)*x(2)) - eta(k) )^2;
%% 	end
%\nb -10 \leq x_i & \leq 10,\ i = 1,2 \\
%
function y = func_for_problem_v_nichtlin_regression(x)
	xi = [1:1:10]';
	eta = [1; 1.1; 1.2; 1.35; 1.55; 1.75; 2.5; 3; 3.7; 4.5];
	m = length(xi);
	y = 0;
	for k=1:m
		y = y + ( x(1)*exp(xi(k)*x(2)) - eta(k) )^2;
	end
end