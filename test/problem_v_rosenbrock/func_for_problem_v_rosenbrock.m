%problem_v_rosenbrock
%(Rosenbrock-Funktion, vgl. Beispiel 1.4.1 in \cite[S.~14]{alt})
%\min_{x\in\R^2}\ 100 (x_2-x_1^2)^2+(1-x_1)^2 
%\nb -10 \leq x_i & \leq 10,\ i = 1,2 \\
%
function y = func_for_problem_v_rosenbrock(x)
	y = 100*(x(2)-x(1)^2)^2+(1-x(1))^2;
end