import React, { useState, type FormEvent } from "react";
import { useAuth } from "../context/AuthContext";
import axios from "axios";

const LoginForm: React.FC = () => {
  const { login } = useAuth();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError(null);

    try {
      const response = await axios.post("/api/usuarios/login", {
        email,
        password,
      });

      login(response.data);
    } catch (err) {
      console.error("Error en el inicio de sesión:", err);
      setError("Credenciales inválidas. Por favor, inténtalo de nuevo.");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      {error && (
        <p className="text-red-500 text-center text-sm mb-4">{error}</p>
      )}
      <div>
        <label
          htmlFor="email-login"
          className="block text-sm font-medium text-gray-700"
        >
          Correo Electrónico
        </label>
        <input
          type="email"
          id="email-login"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
        />
      </div>
      <div>
        <label
          htmlFor="password-login"
          className="block text-sm font-medium text-gray-700"
        >
          Contraseña
        </label>
        <input
          type="password"
          id="password-login"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
        />
      </div>
      <button
        type="submit"
        className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700"
      >
        Iniciar Sesión
      </button>
    </form>
  );
};

const RegisterForm: React.FC = () => {
  const { login } = useAuth();
  const [formData, setFormData] = useState({
    nombre: "",
    email: "",
    telefono: "",
    password: "",
  });
  const [error, setError] = useState<string | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError(null);

    try {
      const response = await axios.post("/api/usuarios/registro", formData);
      console.log("Registro exitoso!", response.data);
      alert(`Usuario ${response.data.nombre} registrado con éxito!`);
      login(response.data);
    } catch (err: any) {
      console.error("--- ERROR DETALLADO EN EL REGISTRO ---");

      if (err.response) {
        console.error("Data:", err.response.data);
        console.error("Status:", err.response.status);
        console.error("Headers:", err.response.headers);
        setError(err.response.data.message || "Error en el servidor.");
      } else if (err.request) {
        console.error("Request:", err.request);
        setError(
          "No se pudo conectar con el servidor. ¿Está el backend corriendo?",
        );
      } else {
        console.error("Error", err.message);
        setError("Ocurrió un error inesperado al enviar los datos.");
      }
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      {error && (
        <p className="text-red-500 text-sm text-center font-semibold">
          {error}
        </p>
      )}
      <div>
        <label
          htmlFor="nombre"
          className="block text-sm font-medium text-gray-700"
        >
          Nombre Completo
        </label>
        <input
          type="text"
          name="nombre"
          id="nombre"
          required
          value={formData.nombre}
          onChange={handleChange}
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
        />
      </div>
      <div>
        <label
          htmlFor="email"
          className="block text-sm font-medium text-gray-700"
        >
          Correo Electrónico
        </label>
        <input
          type="email"
          name="email"
          id="email"
          required
          value={formData.email}
          onChange={handleChange}
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
        />
      </div>
      <div>
        <label
          htmlFor="telefono"
          className="block text-sm font-medium text-gray-700"
        >
          Teléfono
        </label>
        <input
          type="tel"
          name="telefono"
          id="telefono"
          value={formData.telefono}
          onChange={handleChange}
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
        />
      </div>
      <div>
        <label
          htmlFor="password"
          className="block text-sm font-medium text-gray-700"
        >
          Contraseña
        </label>
        <input
          type="password"
          name="password"
          id="password"
          required
          value={formData.password}
          onChange={handleChange}
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
        />
      </div>
      <button
        type="submit"
        className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700"
      >
        Crear Cuenta
      </button>
    </form>
  );
};

const AuthPage: React.FC = () => {
  const [isLogin, setIsLogin] = useState(true);

  return (
    <div className="max-w-md mx-auto bg-white p-8 rounded-xl shadow-lg">
      <div className="mb-6">
        <div className="flex border-b">
          <button
            onClick={() => setIsLogin(true)}
            className={`flex-1 py-2 font-semibold ${isLogin ? "text-blue-600 border-b-2 border-blue-600" : "text-gray-500"}`}
          >
            Iniciar Sesión
          </button>
          <button
            onClick={() => setIsLogin(false)}
            className={`flex-1 py-2 font-semibold ${isLogin ? "text-gray-500" : "text-blue-600 border-b-2 border-blue-600"}`}
          >
            Registrarse
          </button>
        </div>
      </div>
      {isLogin ? <LoginForm /> : <RegisterForm />}
    </div>
  );
};

export default AuthPage;
