package br.com.wjrlabs.commom;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import br.com.digicon.digester.AlgorithmType;
import sun.security.ec.ed.EdDSAParameters.Digester;
import sun.security.ec.ed.EdDSAParameters.DigesterFactory;

/**
 * Objeto para gerenciar o recebimento de arquivos do servidor.
 * 
 */
public class FileReceiving {

	/**
	 * Log da classe.
	 */
	private static final Logger logger = Logger.getLogger(FileReceiving.class);

	/**
	 * Identificador de cada parte do arquivo recebido.
	 * 
	 * 
	 */
	public class Partial implements Serializable, Comparable<Partial> {

		/** Identificador único de versão da classe. */
		private static final long serialVersionUID = 8889316480248407052L;

		/**
		 * Índice da parte.
		 */
		private Short index;

		/**
		 * Parte recebida.
		 */
		private byte[] buffer;

		/**
		 * Construtor padrão.
		 * 
		 * @param index  Índice da parte.
		 * @param buffer Parte recebida
		 */
		public Partial(Short index, byte[] buffer) {
			this.index	= index;
			this.buffer	= buffer;
		}

		/**
		 * @return index
		 */
		public Short getIndex() {
			return index;
		}

		/**
		 * @return buffer
		 */
		public byte[] getBuffer() {
			return buffer;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((index == null) ? 0 : index.hashCode());
			return result;
		}

		 /* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Partial other = (Partial) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (index == null) {
				if (other.index != null)
					return false;
			} else if (!index.equals(other.index))
				return false;
			return true;
		}

		private FileReceiving getOuterType() {
			return FileReceiving.this;
		}

		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Partial o) {
			return index.compareTo(o.getIndex());
		}
	}

	/**
	 * Número Sequencial Único associado a operação.
	 */
	private int nsu;

	/**
	 * Identificador único do arquivo.
	 */
	private UUID uuid;

	/**
	 * Nome do arquivo
	 */
	private String filename;

	/**
	 * HASH para validar o arquivo, no formato MD5.
	 */
	private String hash;

	/**
	 * Tamanho previsto do arquivo.
	 */
	private int length;

	/**
	 * Tamanho de cada parte.
	 */
	private short partialLenght;

	/**
	 * Lista de partes do arquivo.
	 */
	private List<Partial> partials;

	/**
	 * Cálculo de HASH.
	 */
	private Digester digester;

	/**
	 * Guarda o tamanho do arquivo recebido.
	 */
	private int received;
	
	/**
	 * Para ambientes Linux, atribuirá corretamente as permissões 
	 * de leitura e escrita dos arquivos.
	 */
	private Set<PosixFilePermission> permissionsFile;
	
	/**
	 * Para ambientes Linux, atribuirá corretamente as permissões
	 * de leitura e escrita dos diretórios.
	 */
	private Set<PosixFilePermission> permissionsDir;

	/**
	 * Construtor padrão.
	 */
	public FileReceiving() {
		this.partials 			= new ArrayList<Partial>();
		this.digester 			= DigesterFactory.getInstance(AlgorithmType.MD5);
		this.received 			= 0;
		this.permissionsFile	= new HashSet<>();
		this.permissionsFile.add(PosixFilePermission.OWNER_READ);
		this.permissionsFile.add(PosixFilePermission.OWNER_WRITE);
		this.permissionsFile.add(PosixFilePermission.GROUP_READ);
		this.permissionsFile.add(PosixFilePermission.GROUP_WRITE);
		this.permissionsFile.add(PosixFilePermission.OTHERS_READ);
		this.permissionsDir		= new HashSet<>();
		this.permissionsDir.add(PosixFilePermission.OWNER_READ);
		this.permissionsDir.add(PosixFilePermission.OWNER_WRITE);
		this.permissionsDir.add(PosixFilePermission.OWNER_EXECUTE);
		this.permissionsDir.add(PosixFilePermission.GROUP_READ);
		this.permissionsDir.add(PosixFilePermission.GROUP_WRITE);
		this.permissionsDir.add(PosixFilePermission.GROUP_EXECUTE);
		this.permissionsDir.add(PosixFilePermission.OTHERS_READ);
		this.permissionsDir.add(PosixFilePermission.OTHERS_EXECUTE);
	}

	/**
	 * @return nsu
	 */
	public int getNsu() {
		return nsu;
	}

	/**
	 * @param nsu {@link FileReceiving#nsu}
	 */
	public void setNsu(int nsu) {
		this.nsu = nsu;
	}

	/**
	 * @return uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * @param uuid {@link FileReceiving#uuid}
	 */
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename {@link FileReceiving#filename}
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * @param hash {@link FileReceiving#hash}
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * @return length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length {@link FileReceiving#length}
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return partialLenght
	 */
	public short getPartialLenght() {
		return partialLenght;
	}

	/**
	 * @param partialLenght {@link FileReceiving#partialLenght}
	 */
	public void setPartialLenght(short partialLenght) {
		this.partialLenght = partialLenght;
	}
	
	public List<Partial> getPartials() {
		return this.partials;
	}
	
	/**
	 * Adiciona uma nova parte do arquivo.
	 * 
	 * @param index Índice da parte.
	 * @param partial Parte do arquivo
	 */
	public void add(Short index, byte[] partial) {
		// Guarda a parte...
		partials.add(new Partial(index, partial));
		// Calcula o HASH
		digester.update(partial);
		// Guarda o tamanho recebido.
		received += partial.length;
	}

	/**
	 * Verifica se o arquivo recebido está íntegro.
	 * 
	 * @return boolean
	 */
	public boolean isValid() {
		boolean result = false;
		// Se o tamanho recebido é igual ao tamanho informado pelo módulo...
		if (received == length) {
			// Verificar o HASH.
			result = digester.digest().equalsIgnoreCase(hash);
			// Se o HASH for inválido, pode ser problema com a ordem das
			// mensagens.
			// Recalcular...
			if (!result) {
				logger.warn("Hash inválido... reordenando os pacotes e tentando novamente...");
				Collections.sort(partials);
				digester.reset();
				for (Partial partial : partials) {
					digester.update(partial.getBuffer());
				}
				result = digester.digest().equalsIgnoreCase(hash);
				if (!result) {
					logger.error("Hash inválido.");
				}
				// Se ainda for inválido, talvez falte alguma parte...
			}
		} else {
			logger.error(String.format(
					"Recebido %d bytes mas o tamnho esperado é %d bytes. Tamanho não confere", received,
					length));
		}

		return result;
	}

	/**
	 * Salva o arquivo no disco.
	 * 
	 * @param parent Diretório para salvar o arquivo.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void save(String parent) throws FileNotFoundException, IOException {
		File dir = new File(parent);
		File file = new File(dir, filename);
		if (!dir.exists()) {
			dir.mkdirs();
			try {
				Files.setPosixFilePermissions(Paths.get(dir.getPath()), permissionsDir);
			} catch (Exception e) {
			}
		}
		try (FileOutputStream stream = new FileOutputStream(file);
		BufferedOutputStream output = new BufferedOutputStream(stream)) {
			for (Partial partial : partials) {
				output.write(partial.getBuffer());
			}
		}
		// Torna o arquivo acessível ao grupo de usuários...
		try {
			Files.setPosixFilePermissions(Paths.get(file.getPath()), permissionsFile);
		} catch (Exception e) {
		}
	}
}
