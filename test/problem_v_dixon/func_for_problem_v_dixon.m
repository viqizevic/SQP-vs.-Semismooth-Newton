%problem_v_dixon
%(Dixon-Funktion, vgl. Beispiel 1.4.5 in \cite[S.~16]{alt})
%\min_{x\in\R^{10}}\ (1-x_1)^2 + \sum_{k=1}^{9} (x_k^2-x_{k+1})^2 + (1-x_{10})^2
%\nb -10 \leq x_i & \leq 10,\ i = 1,\ldots,10 \\
%
function y = func_for_problem_v_dixon(x)
	n = 10;
	y = (1-x(1))^2;
	for k=1:(n-1)
		y = y + (x(k)^2-x(k+1))^2;
	end
	y = y + (1-x(n))^2;
end