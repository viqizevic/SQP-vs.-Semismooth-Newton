%problem_v_lin_regression
%Lineare Regression (Vgl. Beispiel~\ref{example:lineare_regression})
%\min_{x\in\R^2}\ f(x) %TODO write f(x)
%% y = 0;
%% 	m = length(xi);
%% 	for k=1:m
%% 		y = y + ( x(1)*xi(k) + x(2) - eta(k) )^2;
%% 	end
%\nb -10 \leq x_i & \leq 10,\ i = 1,2 \\
%
function y = func_for_problem_v_lin_regression(x)
	xi = [1:1:10]';
	eta = [-0.5; -2; -3; -3; -2.5; -2; -1; 1; 3; 5.5];
	y = 0;
	m = length(xi);
	for k=1:m
		y = y + ( x(1)*xi(k) + x(2) - eta(k) )^2;
	end
end