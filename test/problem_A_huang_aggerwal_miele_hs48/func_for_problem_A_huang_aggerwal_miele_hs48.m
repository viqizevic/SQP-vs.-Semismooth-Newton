%problem_A_huang_aggerwal_miele_hs48
%(Vgl. Problem 48 in \cite[S.~71]{hock})
%\min_{x\in\R^5}\ (x_1 - 1)^2 + (x_2 - x_3)^2 + (x_4 - x_5)^2
%\nb x_1 + x_2 + x_3 + x_4 + x_5 & = 5 \\
%x_3 - 2 x_4 - 2 x_5 & = -3 \\
%
function y = func_for_problem_A_huang_aggerwal_miele_hs48(x)
	Q = 2*[1 0 0 0 0; 0 1 -1 0 0; 0 -1 1 0 0; 0 0 0 1 -1; 0 0 0 -1 1];
	q = [-2; 0; 0; 0; 0];
	c = 1;
	y = 0.5*x'*Q*x + q'*x + c;
end